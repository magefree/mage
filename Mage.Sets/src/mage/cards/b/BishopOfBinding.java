
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class BishopOfBinding extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BishopOfBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Bishop of Binding enters the battlefield, exile target creature an opponent controls until Bishop of Binding leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BishopOfBindingExileEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // Whenever Bishop of Binding attacks, target Vampire gets +X/+X until end of turn, where X is the power of the exiled card.
        DynamicValue xVale = new BishopOfBindingExiledCardsPowerCount();
        ability = new AttacksTriggeredAbility(new BoostTargetEffect(xVale, xVale, Duration.EndOfTurn, true)
                .setText("target Vampire gets +X/+X until end of turn, where X is the power of the exiled card"), false);
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent(SubType.VAMPIRE, "Vampire")));
        this.addAbility(ability);
    }

    public BishopOfBinding(final BishopOfBinding card) {
        super(card);
    }

    @Override
    public BishopOfBinding copy() {
        return new BishopOfBinding(this);
    }
}

class BishopOfBindingExileEffect extends OneShotEffect {

    public BishopOfBindingExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until {this} leaves the battlefield";
    }

    public BishopOfBindingExileEffect(final BishopOfBindingExileEffect effect) {
        super(effect);
    }

    @Override
    public BishopOfBindingExileEffect copy() {
        return new BishopOfBindingExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        // If this leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}

class BishopOfBindingExiledCardsPowerCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, sourceAbility.getSourceId(), sourceAbility.getSourceObjectZoneChangeCounter()));
        if (exileZone != null) {
            Card exiledCard = exileZone.getRandom(game);
            if (exiledCard != null) {
                return exiledCard.getPower().getValue();
            }
        }
        return 0;
    }

    @Override
    public BishopOfBindingExiledCardsPowerCount copy() {
        return new BishopOfBindingExiledCardsPowerCount();
    }

    @Override
    public String getMessage() {
        return "power of the exiled card";
    }

}
