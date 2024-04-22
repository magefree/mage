package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KrakenOfTheStraits extends CardImpl {

    public KrakenOfTheStraits(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Creatures with power less than the number of Islands you control can't block Kraken of the Straits.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesWithLessPowerEffect()));
    }

    private KrakenOfTheStraits(final KrakenOfTheStraits card) {
        super(card);
    }

    @Override
    public KrakenOfTheStraits copy() {
        return new KrakenOfTheStraits(this);
    }
}

class CantBeBlockedByCreaturesWithLessPowerEffect extends RestrictionEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    private final DynamicValue dynamicValue = new PermanentsOnBattlefieldCount(filter);

    public CantBeBlockedByCreaturesWithLessPowerEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with power less than the number of Islands you control can't block {this}";
    }

    private CantBeBlockedByCreaturesWithLessPowerEffect(final CantBeBlockedByCreaturesWithLessPowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getPower().getValue() >= dynamicValue.calculate(game, source, this);
    }

    @Override
    public CantBeBlockedByCreaturesWithLessPowerEffect copy() {
        return new CantBeBlockedByCreaturesWithLessPowerEffect(this);
    }
}
