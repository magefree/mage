package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class BlueDragon extends CardImpl {

    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filter3 = new FilterCreaturePermanent();

    static {
        filter2.add(new AnotherTargetPredicate(2));
        filter3.add(new AnotherTargetPredicate(3));
    }

    public BlueDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lightning Breath — When Blue Dragon enters the battlefield, until your next turn, target creature an opponent controls gets -3/-0, up to one other target creature gets -2/-0, and up to one other target creature gets -1/-0.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BlueDragonEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent().setTargetTag(1).withChooseHint("-3/-0"));
        ability.addTarget(new TargetPermanent(0, 1, filter2).setTargetTag(2).withChooseHint("-2/-0"));
        ability.addTarget(new TargetPermanent(0, 1, filter3).setTargetTag(3).withChooseHint("-1/-0"));
        this.addAbility(ability.withFlavorWord("Lightning Breath"));
    }

    private BlueDragon(final BlueDragon card) {
        super(card);
    }

    @Override
    public BlueDragon copy() {
        return new BlueDragon(this);
    }
}

class BlueDragonEffect extends ContinuousEffectImpl {

    BlueDragonEffect() {
        super(Duration.UntilYourNextTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        this.staticText = "until your next turn, target creature an opponent controls gets -3/-0, up to one other target creature gets -2/-0, and up to one other target creature gets -1/-0";
    }

    private BlueDragonEffect(final BlueDragonEffect effect) {
        super(effect);
    }

    @Override
    public BlueDragonEffect copy() {
        return new BlueDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int power = -3;
        int affectedTargets = 0;
        for (Target target : source.getTargets()) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null && permanent.isCreature(game)) {
                permanent.addPower(power);
                affectedTargets++;
            }
            power++;
        }
        return affectedTargets > 0;
    }
}
