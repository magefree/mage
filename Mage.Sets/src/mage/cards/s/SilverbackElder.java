package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class SilverbackElder extends CardImpl {

    public SilverbackElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}{G}");

        this.subtype.add(SubType.APE, SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Whenever you cast a creature spell, choose one --
        // * Destroy target artifact or enchantment.
        Ability ability = new SpellCastControllerTriggeredAbility(new DestroyTargetEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // * Look at the top five cards of your library. You may put a land card from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order.
        ability.addMode(new Mode(new LookLibraryAndPickControllerEffect(
                5, 1, StaticFilters.FILTER_CARD_LAND, PutCards.BATTLEFIELD_TAPPED, PutCards.BOTTOM_RANDOM
        )));

        // * You gain 4 life.
        ability.addMode(new Mode(new GainLifeEffect(4)));
        this.addAbility(ability);
    }

    private SilverbackElder(final SilverbackElder card) {
        super(card);
    }

    @Override
    public SilverbackElder copy() {
        return new SilverbackElder(this);
    }
}
