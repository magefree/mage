package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class Reprobation extends CardImpl {

    public Reprobation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature loses all abilities and is a Coward creature with base power and toughness 0/1.
        // (It keeps all supertypes but loses all other types and creature types.)
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new ReprobationToken(), "Enchanted creature loses all abilities " +
                "and is a Coward creature with base power and toughness 0/1. " +
                "<i>(It keeps all supertypes but loses all other types and creature types.)</i>",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ABILITIES_SUBTYPE
        )));
    }

    private Reprobation(final Reprobation card) {
        super(card);
    }

    @Override
    public Reprobation copy() {
        return new Reprobation(this);
    }
}

class ReprobationToken extends TokenImpl {

    ReprobationToken() {
        super("", "loses all abilities and is a Coward creature with base power and toughness 0/1");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.COWARD);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    private ReprobationToken(final ReprobationToken token) {
        super(token);
    }

    public ReprobationToken copy() {
        return new ReprobationToken(this);
    }

}
