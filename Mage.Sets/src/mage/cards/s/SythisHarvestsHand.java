package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SythisHarvestsHand extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public SythisHarvestsHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NYMPH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you cast an enchantment spell, you gain 1 life and draw a card.
        Ability ability = new SpellCastControllerTriggeredAbility(new GainLifeEffect(1), filter, false);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SythisHarvestsHand(final SythisHarvestsHand card) {
        super(card);
    }

    @Override
    public SythisHarvestsHand copy() {
        return new SythisHarvestsHand(this);
    }
}
