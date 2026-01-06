package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.BeholdAndExileCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnExiledCardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.BeholdType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChampionsOfThePerfect extends CardImpl {

    public ChampionsOfThePerfect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // As an additional cost to cast this spell, behold an Elf and exile it.
        this.getSpellAbility().addCost(new BeholdAndExileCost(BeholdType.ELF));

        // Whenever you cast a creature spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));

        // When this creature leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnExiledCardToHandEffect()));
    }

    private ChampionsOfThePerfect(final ChampionsOfThePerfect card) {
        super(card);
    }

    @Override
    public ChampionsOfThePerfect copy() {
        return new ChampionsOfThePerfect(this);
    }
}
