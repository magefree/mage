package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcticFoxes extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 1));
    }

    public ArcticFoxes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.FOX);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Creatures with power 2 or greater can't block Arctic Foxes as long as defending player controls a snow land.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield),
                ArcticFoxesCondition.instance, "creatures with power 2 or greater can't block {this}" +
                " as long as defending player controls a snow land"
        )));
    }

    private ArcticFoxes(final ArcticFoxes card) {
        super(card);
    }

    @Override
    public ArcticFoxes copy() {
        return new ArcticFoxes(this);
    }
}

enum ArcticFoxesCondition implements Condition {
    instance;

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SuperType.SNOW.getPredicate());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defenderId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        if (defenderId == null) {
            return false;
        }
        return game.getBattlefield().contains(filter, source.getSourceId(), defenderId, source, game, 1);
    }
}