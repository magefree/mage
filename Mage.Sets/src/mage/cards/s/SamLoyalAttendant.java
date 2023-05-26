package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamLoyalAttendant extends CardImpl {

    public SamLoyalAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Partner with Frodo, Adventurous Hobbit
        this.addAbility(new PartnerWithAbility("Frodo, Adventurous Hobbit"));

        // At the beginning of combat on your turn, create a Food token.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new CreateTokenEffect(new FoodToken()), TargetController.YOU, false
        ));

        // Activated abilities of Foods you control cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(new SamLoyalAttendantEffect()));
    }

    private SamLoyalAttendant(final SamLoyalAttendant card) {
        super(card);
    }

    @Override
    public SamLoyalAttendant copy() {
        return new SamLoyalAttendant(this);
    }
}

class SamLoyalAttendantEffect extends CostModificationEffectImpl {

    SamLoyalAttendantEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "activated abilities of Foods you control cost {1} less to activate";
    }

    private SamLoyalAttendantEffect(SamLoyalAttendantEffect effect) {
        super(effect);
    }

    @Override
    public SamLoyalAttendantEffect copy() {
        return new SamLoyalAttendantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof ActivatedAbility)) {
            return false;
        }
        Permanent permanent = abilityToModify.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.hasSubtype(SubType.FOOD, game);
    }
}
