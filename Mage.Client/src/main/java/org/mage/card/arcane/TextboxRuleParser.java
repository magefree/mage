package org.mage.card.arcane;

import mage.cards.repository.CardInfo;
import mage.view.CardView;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author StravantUser
 */
public final class TextboxRuleParser {

    private static final Logger LOGGER = Logger.getLogger(TextboxRuleParser.class);

    private static final Pattern BasicManaAbility = Pattern.compile("\\{T\\}: Add \\{(\\w)\\}\\.");
    private static final Pattern LevelAbilityPattern = Pattern.compile("Level (\\d+)-?(\\d*)(\\+?)");
    private static final Pattern LoyaltyAbilityPattern = Pattern.compile("^(\\+|\\-)(\\d+|X): ");
    private static final Pattern SimpleKeywordPattern = Pattern.compile("^(\\w+( \\w+)?)\\s*(\\([^\\)]*\\))?\\s*$");
    private static final Pattern FontColorValuePattern = Pattern.compile("color\\s*=\\s*[\"'](\\w+)[\"']");

    // Parse a given rule (given as a string) into a TextboxRule, replacing
    // symbol annotations, italics, etc, parsing out information such as
    // if the ability is a loyalty ability, and returning an TextboxRule
    // representing that information, which can be used to render the rule in
    // the textbox of a card.
    public static TextboxRule parse(CardView source, String rule, String cardNameToUse) {
        // List of regions to apply
        List<TextboxRule.AttributeRegion> regions = new ArrayList<>();

        // Leveler / loyalty / basic
        boolean isLeveler = false;
        int levelFrom = 0;
        int levelTo = 0;

        boolean isLoyalty = false;
        int loyaltyChange = 0;

        boolean isBasicMana = false;
        String basicManaSymbol = "";

        // Parse the attributedString contents
        int index = 0;
        int outputIndex = 0;

        // Is it a simple keyword ability?
        {
            Matcher simpleKeywordMatch = SimpleKeywordPattern.matcher(rule);
            if (simpleKeywordMatch.find()) {
                return new TextboxKeywordRule(simpleKeywordMatch.group(1), regions);
            }
        }

        // Is it a basic mana ability?
        {
            Matcher basicManaMatcher = BasicManaAbility.matcher(rule);
            if (basicManaMatcher.find()) {
                isBasicMana = true;
                basicManaSymbol = basicManaMatcher.group(1);
            }
        }

        // Check if it's a loyalty ability. Must be right at the start of the rule
        {
            Matcher loyaltyMatch = LoyaltyAbilityPattern.matcher(rule);
            if (loyaltyMatch.find()) {
                // Get the loyalty change
                if (loyaltyMatch.group(2).equals("X")) {
                    loyaltyChange = TextboxLoyaltyRule.MINUS_X;
                } else {
                    loyaltyChange = Integer.parseInt(loyaltyMatch.group(2));
                    if (loyaltyMatch.group(1).equals("-")) {
                        loyaltyChange = -loyaltyChange;
                    }
                }
                isLoyalty = true;

                // Go past the match
                index = loyaltyMatch.group().length();
            }
        }

        Deque<Integer> openingStack = new ArrayDeque<>();
        StringBuilder build = new StringBuilder();
        while (index < rule.length()) {
            int initialIndex = index;
            char ch = rule.charAt(index);
            switch (ch) {
                case '{': {
                    // Handling for `{this}`
                    int closeIndex = rule.indexOf('}', index);
                    if (closeIndex == -1) {
                        // Malformed input, nothing to do
                        ++index;
                        ++outputIndex;
                        build.append(ch);
                    } else {
                        String contents = rule.substring(index + 1, closeIndex);
                        if (contents.equals("this") || contents.equals("source")) {
                            // Replace {this} with the card's name
                            build.append(cardNameToUse);
                            index += contents.length() + 2;
                            outputIndex += cardNameToUse.length();
                        } else {
                            Image symbol = ManaSymbols.getSizedManaSymbol(contents.replace("/", ""), 10);
                            if (symbol != null) {
                                // Mana or other inline symbol
                                build.append('#');
                                regions.add(new TextboxRule.EmbeddedSymbol(contents, outputIndex));
                                ++outputIndex;
                                index = closeIndex + 1;
                            } else {
                                // Bad entry
                                if (contents.equals(CardInfo.SPLIT_MANA_SEPARATOR_FULL)) {
                                    build.append(CardInfo.SPLIT_MANA_SEPARATOR_RENDER);
                                    outputIndex += contents.length() - CardInfo.SPLIT_MANA_SEPARATOR_RENDER.length();
                                } else {
                                    build.append('{');
                                    build.append(contents);
                                    build.append('}');
                                    outputIndex += (contents.length() + 2);
                                }
                                index = closeIndex + 1;
                            }
                        }
                    }
                    break;
                }
                case '&':
                    // Handling for `&mdash;`
                    if (rule.startsWith("&mdash;", index)) {
                        build.append('—');
                        index += 7;
                        ++outputIndex;
                    } else if (rule.startsWith("&bull", index)) {
                        build.append('•');
                        index += 5;
                        ++outputIndex;
                    } else {
                        LOGGER.error("Bad &...; sequence `" + rule.substring(index + 1, index + 10) + "` in rule.");
                        build.append('&');
                        ++index;
                        ++outputIndex;
                    }
                    break;
                case '<': {
                    // Handling for `<i>` and `<br/>`
                    int closeIndex = rule.indexOf('>', index);
                    if (closeIndex != -1) {
                        // Is a tag
                        String tag = rule.substring(index + 1, closeIndex);
                        if (tag.charAt(tag.length() - 1) == '/') {
                            // Pure closing tag (like <br/>)
                            if (tag.equals("br/")) {
                                build.append('\n');
                                ++outputIndex;
                            } else {
                                // Unknown
                                build.append('<').append(tag).append('>');
                                outputIndex += (tag.length() + 2);
                            }
                        } else if (tag.charAt(0) == '/') {
                            // Opening index for the tag
                            int openingIndex;
                            if (openingStack.isEmpty()) {
                                // Malformed input, just make an empty interval
                                openingIndex = outputIndex;
                            } else {
                                openingIndex = openingStack.pop();
                            }

                            // What tag is it?
                            switch (tag) {
                                case "/i":
                                    // Italics
                                    regions.add(new TextboxRule.ItalicRegion(openingIndex, outputIndex));
                                    break;
                                case "/b":
                                    // Bold, see if it's a level ability
                                    String content = build.substring(openingIndex);
                                    Matcher levelMatch = LevelAbilityPattern.matcher(content);
                                    if (levelMatch.find()) {
                                        try {
                                            levelFrom = Integer.parseInt(levelMatch.group(1));
                                            if (!levelMatch.group(2).isEmpty()) {
                                                levelTo = Integer.parseInt(levelMatch.group(2));
                                            }
                                            if (!levelMatch.group(3).isEmpty()) {
                                                levelTo = TextboxLevelRule.AND_HIGHER;
                                            }
                                            isLeveler = true;
                                        } catch (Exception e) {
                                            LOGGER.error("Bad leveler levels in rule `" + rule + "`.");
                                        }
                                    }
                                    break;
                                case "/font":
                                    // Font it is an additional info of a card
                                    // lets make it blue like it is in tooltip
                                    regions.add(new TextboxRule.ColorRegion(openingIndex, outputIndex, Color.BLUE));
                                    break;
                                default:
                                    // Unknown
                                    build.append('<').append(tag).append('>');
                                    outputIndex += (tag.length() + 2);
                                    break;
                            }
                        } else // Is it a <br> tag special case? [Why can't it have a closing `/`... =( ]
                        {
                            if (tag.equals("br")) {
                                build.append('\n');
                                ++outputIndex;
                            } else {
                                // Opening tag
                                openingStack.push(outputIndex);
                            }
                        }
                        // Skip characters
                        index = closeIndex + 1;
                    } else {
                        // Malformed tag
                        build.append('<');
                        ++outputIndex;
                        ++index;
                    }
                    break;
                }
                default:
                    // Normal character
                    ++index;
                    ++outputIndex;
                    build.append(ch);
                    break;
            }
            if (outputIndex != build.length()) {
                // Somehow our parsing code output symbols but didn't update the output index correspondingly
                LOGGER.error("The human is dead; mismatch! Failed on rule: `" + rule + "` due to not updating outputIndex properly.");

                // Bail out
                build = new StringBuilder(rule);
                regions.clear();
                break;
            }
            if (index == initialIndex) {
                // Somehow our parsing failed to consume the
                LOGGER.error("Failed on rule `" + rule + "` due to not consuming a character.");

                // Bail out
                build = new StringBuilder(rule);
                regions.clear();
                break;
            }
        }

        // Build and return the rule
        rule = build.toString();
        if (isLoyalty) {
            return new TextboxLoyaltyRule(rule, regions, loyaltyChange);
        } else if (isLeveler) {
            return new TextboxLevelRule(rule, regions, levelFrom, levelTo);
        } else if (isBasicMana) {
            return new TextboxBasicManaRule(rule, regions, basicManaSymbol);
        } else {
            return new TextboxRule(rule, regions);
        }
    }
}
