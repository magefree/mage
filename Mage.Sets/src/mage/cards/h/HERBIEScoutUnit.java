package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HERBIEScoutUnit extends CardImpl {

    public HERBIEScoutUnit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, draw a card, then you may put a land card from your hand onto the battlefield tapped.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A, false, true)
            .concatBy(", then"));
        this.addAbility(ability);
    }

    private HERBIEScoutUnit(final HERBIEScoutUnit card) {
        super(card);
    }

    @Override
    public HERBIEScoutUnit copy() {
        return new HERBIEScoutUnit(this);
    }
}
