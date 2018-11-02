package utils;

import java.util.Map;
import java.util.function.Function;

public class Bioinformatics
{
	public static final char DEFAULT_UNAVAILABLE_CHARACTER = '-';
	private static final Map<Character, Character>
		DNA_NUCLEOTIDE_COMPLIMENTS = Map.ofEntries(
				Map.entry('A', 'T'),
				Map.entry('T', 'A'),
				Map.entry('C', 'G'),
				Map.entry('G', 'C')
		);
	private static final Map<Character, Character>
		RNA_NUCLEOTIDE_COMPLIMENTS = Map.ofEntries(
				Map.entry('A', 'U'),
				Map.entry('U', 'A'),
				Map.entry('C', 'G'),
				Map.entry('G', 'C')
		);
	private static final Map<Character, Character>
		RNA_TO_DNA_NUCLEOTIDE_COMPLIMENTS = Map.ofEntries(
				Map.entry('T', 'A'), Map.entry('A', 'U'),
				Map.entry('C', 'G'), Map.entry('G', 'C')
			);
	private static final Map<String, Character>
		RNA_AMINO_ACIDS = Map.ofEntries(
				Map.entry("UUU", 'F'), Map.entry("UUC", 'F'),
				Map.entry("UUA", 'L'), Map.entry("UUG", 'L'),
				Map.entry("CUU", 'L'), Map.entry("CUC", 'L'),
				Map.entry("CUA", 'L'), Map.entry("CUG", 'L'),
				Map.entry("AUU", 'I'), Map.entry("AUC", 'I'),
				Map.entry("AUG", 'M'), Map.entry("GUU", 'V'),
				Map.entry("UCU", 'S'), Map.entry("UCC", 'S'),
				Map.entry("UCA", 'S'), Map.entry("UCG", 'S'),
				Map.entry("AGU", 'S'), Map.entry("AGC", 'S'),
				Map.entry("CCU", 'P'), Map.entry("CCC", 'P'),
				Map.entry("CCA", 'P'), Map.entry("CCG", 'P'),
				Map.entry("ACU", 'T'), Map.entry("ACC", 'T'),
				Map.entry("ACA", 'T'), Map.entry("ACG", 'T'),
				Map.entry("GCU", 'A'), Map.entry("GCC", 'A'),
				Map.entry("GCA", 'A'), Map.entry("GCG", 'A'),
				Map.entry("UAU", 'Y'), Map.entry("UAC", 'Y'),
				Map.entry("UAA", '*'), Map.entry("UAG", '*'),
				Map.entry("CAU", 'H'), Map.entry("CAC", 'H'),
				Map.entry("CAA", 'Q'), Map.entry("CAG", 'Q'),
				Map.entry("AAU", 'N'), Map.entry("AAC", 'N'),
				Map.entry("AAA", 'K'), Map.entry("AAG", 'K'),
				Map.entry("GAU", 'D'), Map.entry("GAC", 'D'),
				Map.entry("GAA", 'E'), Map.entry("GAG", 'E'),
				Map.entry("UGU", 'C'), Map.entry("UGC", 'C'),
				Map.entry("UGA", '*'), Map.entry("UGG", 'W'),
				Map.entry("CGU", 'R'), Map.entry("CGC", 'R'),
				Map.entry("CGA", 'R'), Map.entry("CGG", 'R'),
				Map.entry("AGA", 'R'), Map.entry("AGG", 'R'),
				Map.entry("GGU", 'G'), Map.entry("GGC", 'G'),
				Map.entry("GGA", 'G'), Map.entry("GGG", 'G'),
				Map.entry("GUC", 'V'), Map.entry("GUA", 'V'),
				Map.entry("GUG", 'V'), Map.entry("AUA", 'I')
		);
	private static final Map<Character, String>
		AMINO_ACIDS_EXPANDED = Map.ofEntries(
				Map.entry('A', "Ala"), Map.entry('R', "Arg"),
				Map.entry('N', "Asn"), Map.entry('D', "Asp"),
				Map.entry('C', "Cys"), Map.entry('E', "Glu"),
				Map.entry('Q', "Gln"), Map.entry('G', "Gly"),
				Map.entry('H', "His"), Map.entry('I', "Ile"),
				Map.entry('L', "Leu"), Map.entry('K', "Lys"),
				Map.entry('M', "Met"), Map.entry('F', "Phe"),
				Map.entry('P', "Pro"), Map.entry('S', "Ser"),
				Map.entry('T', "Thr"), Map.entry('W', "Trp"),
				Map.entry('Y', "Tyr"), Map.entry('V', "Val"),
				Map.entry('*', "*")
		);
	
	private static StringBuilder constructStringBuilder(String sequence, Function<Character, String> func, int initialLength)
	{
		StringBuilder newSequence = new StringBuilder(initialLength);
		sequence.chars().forEach(
				c -> newSequence.append(func.apply((char) c)));
		return newSequence;
	}
	
	private static StringBuilder constructStringBuilderToCharacter(String sequence, Function<Character, Character> func, int initialLength)
	{
		StringBuilder newSequence = new StringBuilder(initialLength);
		sequence.chars().forEach(
				c -> newSequence.append(func.apply((char) c)));
		return newSequence;
	}
	
	public static char dnaNucleotideCompliment(char nucleotide)
	{
		return DNA_NUCLEOTIDE_COMPLIMENTS.getOrDefault(Character.toUpperCase(nucleotide),
				DEFAULT_UNAVAILABLE_CHARACTER);
	}
	
	public static char rnaNucleotideCompliment(char nucleotide)
	{
		return RNA_NUCLEOTIDE_COMPLIMENTS.getOrDefault(Character.toUpperCase(nucleotide),
				DEFAULT_UNAVAILABLE_CHARACTER);
	}
	
	public static String expandedAminoAcid(char aminoAcid)
	{
		return AMINO_ACIDS_EXPANDED.getOrDefault(Character.toUpperCase(aminoAcid),
				String.valueOf(DEFAULT_UNAVAILABLE_CHARACTER));
	}
	
	public static String expandedAminoAcidSequence(String aminoAcidSequence)
	{
		aminoAcidSequence = stripWhiteSpace(aminoAcidSequence);
		return constructStringBuilder(aminoAcidSequence,
				Bioinformatics::expandedAminoAcid,
				aminoAcidSequence.length() * 3).toString();
	}
	
	public static String dnaSequenceCompliment(String sequence)
	{	
		sequence = stripWhiteSpace(sequence);
		return constructStringBuilderToCharacter(sequence,
				Bioinformatics::dnaNucleotideCompliment,
				sequence.length()).toString();
	}
	
	public static String rnaSequenceCompliment(String sequence)
	{	
		sequence = stripWhiteSpace(sequence);
		return constructStringBuilderToCharacter(sequence,
				Bioinformatics::rnaNucleotideCompliment,
				sequence.length()).toString();
	}
	
	public static String dnaReverseCompliment(String sequence)
	{
		sequence = stripWhiteSpace(sequence);
		return constructStringBuilderToCharacter(sequence,
				Bioinformatics::dnaNucleotideCompliment,
				sequence.length()).reverse().toString();
	}
	
	public static String rnaReverseCompliment(String sequence)
	{
		sequence = stripWhiteSpace(sequence);
		return constructStringBuilderToCharacter(sequence,
				Bioinformatics::rnaNucleotideCompliment,
				sequence.length()).reverse().toString();
	}
	
	public static char rnaToAminoAcid(String rna)
	{
		return RNA_AMINO_ACIDS.getOrDefault(rna.toUpperCase(),
				DEFAULT_UNAVAILABLE_CHARACTER);
	}
	
	public static String rnaSequenceToAminoAcids(String rnaSequence)
	{
		return rnaSequenceToAminoAcids(rnaSequence, 1);
	}
	
	public static String rnaSequenceToAminoAcids(String rnaSequence, int readingFrame)
	{
		StringBuilder newSequence = new StringBuilder(rnaSequence.length() / 3);
		if(readingFrame < 0)
			rnaSequence = rnaReverseCompliment(rnaSequence);
		
		for(int lead = Math.abs(readingFrame) - 1; lead < rnaSequence.length(); lead += 3)
		{
			try
			{
				newSequence.append(rnaToAminoAcid(rnaSequence.substring(lead, lead + 3)));
			}
			catch(StringIndexOutOfBoundsException ex)
			{
				break;
			}
		}
		
		if(readingFrame < 0)
			newSequence = newSequence.reverse();
		
		return newSequence.toString();
	}
	
	public static String dnaToRna(String seq)
	{
		seq = stripWhiteSpace(seq.toUpperCase());
		StringBuilder rna = new StringBuilder(seq.length());
		seq.chars().forEach(n -> rna.append(RNA_TO_DNA_NUCLEOTIDE_COMPLIMENTS.get((char) n)));
		return rna.toString();
	}
	
	public static String dnaSequenceToAminoAcids(String dnaSequence)
	{
		return dnaSequenceToAminoAcids(dnaSequence, 1);
	}
	
	public static String dnaSequenceToAminoAcids(String dnaSequence, int readingFrame)
	{
		return rnaSequenceToAminoAcids(dnaToRna(dnaSequence), readingFrame);
	}
	
	private static String stripWhiteSpace(String input)
	{
		return input.replaceAll("\\p{javaWhitespace}", "");
	}
}
