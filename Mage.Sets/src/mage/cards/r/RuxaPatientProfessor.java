package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NoAbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuxaPatientProfessor extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card with no abilities from your graveyard");
    private static final FilterCreaturePermanent filter2
            = new FilterCreaturePermanent("creatures you control with no abilities");

    static {
        filter.add(NoAbilityPredicate.instance);
        filter2.add(NoAbilityPredicate.instance);
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public RuxaPatientProfessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Ruxa, Patient Professor enters the battlefield or attacks, return target creature card with no abilities from your graveyard to your hand.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Creatures you control with no abilities get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter2, false
        )));

        // You may have creatures you control with no abilities assign their combat damage as though they weren't blocked.
        this.addAbility(new SimpleStaticAbility(new RuxaPatientProfessorEffect()));
    }

    private RuxaPatientProfessor(final RuxaPatientProfessor card) {
        super(card);
    }

    @Override
    public RuxaPatientProfessor copy() {
        return new RuxaPatientProfessor(this);
    }
}

class RuxaPatientProfessorEffect extends AsThoughEffectImpl {

    RuxaPatientProfessorEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "for each creature you control with no abilities, you may have " +
                "that creature assign its combat damage as though it weren't blocked.";
    }

    private RuxaPatientProfessorEffect(RuxaPatientProfessorEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(sourceId);
        return controller != null
                && permanent != null
                && permanent.isControlledBy(controller.getId())
                && NoAbilityPredicate.instance.apply(permanent, game)
                && controller.chooseUse(Outcome.Damage, "Have " + permanent.getLogName()
                + " assign damage as though it weren't blocked?", source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RuxaPatientProfessorEffect copy() {
        return new RuxaPatientProfessorEffect(this);
    }
}
