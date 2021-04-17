package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BedlamReveler extends CardImpl {

    public BedlamReveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.DEVIL, SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // This spell costs {1} less to cast for each instant and sorcery card in your graveyard.
        DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue));
        ability.setRuleAtTheTop(true);
        ability.addHint(new ValueHint("Instant and sorcery card in your graveyard", xValue));
        this.addAbility(ability);

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Bedlam Reveler enters the battlefield, discard your hand, then draw three cards.
        ability = new EntersBattlefieldTriggeredAbility(new DiscardHandControllerEffect());
        ability.addEffect(new DrawCardSourceControllerEffect(3).concatBy(", then"));
        this.addAbility(ability);
    }

    private BedlamReveler(final BedlamReveler card) {
        super(card);
    }

    @Override
    public BedlamReveler copy() {
        return new BedlamReveler(this);
    }
}
