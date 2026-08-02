package mage.cards.d;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AxeToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author muz
 */
public final class DainIronfoot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each equipped attacking creature");

    static {
        filter.add(Predicates.and(AttackingPredicate.instance, EquippedPredicate.instance));
    }

    public DainIronfoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // When Dain enters, create a colorless Equipment artifact token named Axe with "Equipped creature gets +1/+0" and equip {2}. When you do, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DainIronfootCreateAxeEffect()));

        // Whenever Dain attacks, each equipped attacking creature gains double strike until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
            new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, filter)
        ));
    }

    private DainIronfoot(final DainIronfoot card) {
        super(card);
    }

    @Override
    public DainIronfoot copy() {
        return new DainIronfoot(this);
    }
}

class DainIronfootCreateAxeEffect extends OneShotEffect {

    DainIronfootCreateAxeEffect() {
        super(Outcome.Benefit);
        staticText = "create a colorless Equipment artifact token named Axe with " +
            "\"Equipped creature gets +1/+0\" and equip {2}. When you do, attach it to target creature you control";
    }

    private DainIronfootCreateAxeEffect(final DainIronfootCreateAxeEffect effect) {
        super(effect);
    }

    @Override
    public DainIronfootCreateAxeEffect copy() {
        return new DainIronfootCreateAxeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new AxeToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        Permanent axe = token.getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (axe == null) {
            return false;
        }
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new DainIronfootAttachEffect(new MageObjectReference(axe, game)), false,
                "when you do, attach it to target creature you control"
        );
        reflexive.addTarget(new TargetControlledCreaturePermanent());
        game.fireReflexiveTriggeredAbility(reflexive, source);
        return true;
    }
}

class DainIronfootAttachEffect extends OneShotEffect {

    private final MageObjectReference mor;

    DainIronfootAttachEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
        staticText = "attach it to target creature you control";
    }

    private DainIronfootAttachEffect(final DainIronfootAttachEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public DainIronfootAttachEffect copy() {
        return new DainIronfootAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Permanent axe = mor.getPermanent(game);
        return creature != null && axe != null && creature.addAttachment(axe.getId(), source, game);
    }
}
