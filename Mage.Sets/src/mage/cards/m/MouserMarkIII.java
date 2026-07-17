package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class MouserMarkIII extends CardImpl {

    public MouserMarkIII(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U/R}");

        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // This creature can't attack unless you control another artifact.
        this.addAbility(new SimpleStaticAbility(new MouserMarkIIIEffect()));
    }

    private MouserMarkIII(final MouserMarkIII card) {
        super(card);
    }

    @Override
    public MouserMarkIII copy() {
        return new MouserMarkIII(this);
    }
}

class MouserMarkIIIEffect extends RestrictionEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public MouserMarkIIIEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "This creature can't attack unless you control another artifact";
    }

    private MouserMarkIIIEffect(final MouserMarkIIIEffect effect) {
        super(effect);
    }

    @Override
    public MouserMarkIIIEffect copy() {
        return new MouserMarkIIIEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getId().equals(source.getSourceId())) {
            return game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).isEmpty();
        }
        return false;
    }
}
