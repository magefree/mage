package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OssuaryRats extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);
    private static final Hint hint = new ValueHint("Creatures in your graveyard", xValue);

    public OssuaryRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.RAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Ossuary Rats enters the battlefield, it deals X damage to target creature or planeswalker an opponent controls, where X is the number of creature cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(xValue, "it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability.addHint(hint));
    }

    private OssuaryRats(final OssuaryRats card) {
        super(card);
    }

    @Override
    public OssuaryRats copy() {
        return new OssuaryRats(this);
    }
}
