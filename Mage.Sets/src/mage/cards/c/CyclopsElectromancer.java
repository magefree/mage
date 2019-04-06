package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CyclopsElectromancer extends CardImpl {

    private static final DynamicValue xValue
            = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);

    public CyclopsElectromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Cyclops Electromancer enters the battlefield, it deals X damage to target creature an opponent controls, where X is the number of instant and sorcery cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(xValue).setText("it deals X damage to target creature an opponent controls, " +
                        "where X is the number of instant and sorcery cards in your graveyard.")
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private CyclopsElectromancer(final CyclopsElectromancer card) {
        super(card);
    }

    @Override
    public CyclopsElectromancer copy() {
        return new CyclopsElectromancer(this);
    }
}
