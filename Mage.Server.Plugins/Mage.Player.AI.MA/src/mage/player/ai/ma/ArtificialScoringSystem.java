package mage.player.ai.ma;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author ubeefx, nantuko
 */
public class ArtificialScoringSystem {

    public static final int WIN_GAME_SCORE=100000000;
    public static final int LOSE_GAME_SCORE=-WIN_GAME_SCORE;

    private static final int LIFE_SCORES[] = {0, 1000, 2000, 3000, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7400, 7800, 8200, 8600, 9000, 9200, 9400, 9600, 9800, 10000};
    private static final int MAX_LIFE = LIFE_SCORES.length - 1;
    private static final int UNKNOWN_CARD_SCORE = 300;
    private static final int PERMANENT_SCORE = 300;
    private static final int LIFE_ABOVE_MULTIPLIER = 100;

    public static int getCardDefinitionScore(final Game game, final Card card) {
        int value = 3; //TODO: add new rating system card value
        if (card.getCardType().contains(Constants.CardType.LAND)) {
            int score = (int) ((value / 2.0f) * 50);
            //TODO: check this for "any color" lands
            //TODO: check this for dual and filter lands
            /*for (Mana mana : card.getMana()) {
                score += 50;
            }*/
            score += card.getMana().size()*50;
            return score;
        }

        final int score = value * 100 - card.getManaCost().convertedManaCost() * 20;
        if (card.getCardType().contains(Constants.CardType.CREATURE)) {
            return score + (card.getPower().getValue() + card.getToughness().getValue()) * 10;
        } else {
            return score + (/*card.getRemoval()*50*/ +card.getRarity().getRating() * 30);
        }
    }

    public static int getFixedPermanentScore(final Game game, final Permanent permanent) {
        //TODO: cache it inside Card
        int score = getCardDefinitionScore(game, permanent);
        if (permanent.getCardType().contains(Constants.CardType.CREATURE)) {
            // TODO: implement in the mage core
            //score + =cardDefinition.getActivations().size()*50;
            //score += cardDefinition.getManaActivations().size()*80;
        } else {
            score += PERMANENT_SCORE;
            if (permanent.getSubtype().contains("Equipment")) {
                score += 100;
            }
        }
        return score;
    }

    public static int getVariablePermanentScore(final Game game, final Permanent permanent) {

        int score = permanent.getCounters().getCount(CounterType.CHARGE) * 30;
        score += permanent.getCounters().getCount(CounterType.LEVEL) * 30;
        score -= permanent.getDamage() * 2;
        if (!canTap(permanent)) {
            score += getTappedScore(permanent);
        }
        if (permanent.getCardType().contains(Constants.CardType.CREATURE)) {
            final int power = permanent.getPower().getValue();
            final int toughness = permanent.getToughness().getValue();
            int abilityScore = 0;
            for (Ability ability : permanent.getAbilities()) {
                abilityScore += MagicAbility.getAbilityScore(ability);
            }
            score += power * 300 + getPositive(toughness) * 200 + abilityScore * (getPositive(power) + 1) / 2;
            //TODO: it can be improved
            //score += permanent.getEquipmentPermanents().size() * 50 + permanent.getAuraPermanents().size() * 100;
            int enchantments = 0;
            int equipments = 0;
            for (UUID uuid : permanent.getAttachments()) {
                Card card = game.getCard(uuid);
                if (card != null) {
                    if (card.getCardType().contains(Constants.CardType.ENCHANTMENT)) {
                        enchantments++;
                    } else {
                        equipments++;
                    }
                }
            }
            score += equipments*50 /*+ enchantments*100*/;

            if (!permanent.canAttack(game)) {
                score -= 100;
            }

            if (!permanent.canBlockAny(game)) {
                score -= 30;
            }
        }
        return score;
    }

    private static boolean canTap(Permanent permanent) {
        return !permanent.isTapped()
                &&(!permanent.hasSummoningSickness()
                    ||!permanent.getCardType().contains(Constants.CardType.CREATURE)
                    ||permanent.getAbilities().contains(HasteAbility.getInstance()));
    }

    private static int getPositive(int value) {
        return value > 0 ? value : 0;
    }

    public static int getTappedScore(final Permanent permanent) {
        if (permanent.getCardType().contains(Constants.CardType.CREATURE)) {
            return -100;
        } else if (permanent.getCardType().contains(Constants.CardType.LAND)) {
            return -1;
        } else {
            return -2;
        }
    }

    public static int getLifeScore(final int life) {
        if (life > MAX_LIFE) {
            return LIFE_SCORES[MAX_LIFE] + (life - MAX_LIFE) * LIFE_ABOVE_MULTIPLIER;
        } else if (life >= 0) {
            return LIFE_SCORES[life];
        } else {
            return 0;
        }
    }

    public static int getManaScore(final int amount) {
        return -amount;
    }

    public static int getAttackerScore(final Permanent attacker) {
        //TODO: implement this
        /*int score = attacker.getPower().getValue() * 5 + attacker.lethalDamage * 2 - attacker.candidateBlockers.length;
        for (final MagicCombatCreature blocker : attacker.candidateBlockers) {

            score -= blocker.power;
        }
        // Dedicated attacker.
        if (attacker.hasAbility(MagicAbility.AttacksEachTurnIfAble) || attacker.hasAbility(MagicAbility.CannotBlock)) {
            score += 10;
        }
        // Abilities for attacking.
        if (attacker.hasAbility(MagicAbility.Trample) || attacker.hasAbility(MagicAbility.Vigilance)) {
            score += 8;
        }
        // Dangerous to block.
        if (!attacker.normalDamage || attacker.hasAbility(MagicAbility.FirstStrike) || attacker.hasAbility(MagicAbility.Indestructible)) {
            score += 7;
        }
        */
        int score = 0;
        return score;
    }
}
