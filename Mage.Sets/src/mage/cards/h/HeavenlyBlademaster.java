package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AuraAttachedCount;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeavenlyBlademaster extends CardImpl {

    public HeavenlyBlademaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When Heavenly Blademaster enters the battlefield, you may attach any number of Auras and Equipment you control to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new HeavenlyBlademasterEffect(), true
        ));

        // Other creatures you control get +1/+1 for each Aura and Equipment attached to Heavenly Blademaster.
        DynamicValue totalAmount = new AdditiveDynamicValue(
                new EquipmentAttachedCount(1),
                new AuraAttachedCount(1)
        );
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostControlledEffect(
                        totalAmount, totalAmount, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_CREATURES, true
                ).setText("Other creatures you control get +1/+1 for each Aura and Equipment attached to {this}")
        ));
    }

    private HeavenlyBlademaster(final HeavenlyBlademaster card) {
        super(card);
    }

    @Override
    public HeavenlyBlademaster copy() {
        return new HeavenlyBlademaster(this);
    }
}

class HeavenlyBlademasterEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("Aura or Equipment you control");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public HeavenlyBlademasterEffect() {
        super(Outcome.Benefit);
        staticText = "you may attach any number of Auras and Equipment you control to it";
    }

    public HeavenlyBlademasterEffect(final HeavenlyBlademasterEffect effect) {
        super(effect);
    }

    @Override
    public HeavenlyBlademasterEffect copy() {
        return new HeavenlyBlademasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePermanent == null || player == null) {
            return false;
        }
        Target target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        target.getTargets().stream().map(
                attachmentId -> game.getPermanent(attachmentId)
        ).filter(
                attachment -> attachment != null
        ).forEachOrdered((attachment) -> {
            if (!sourcePermanent.cantBeAttachedBy(attachment, source, game, true)) {
                if (attachment.getAttachedTo() != sourcePermanent.getId()) {
                    if (attachment.getAttachedTo() != null) {
                        Permanent fromPermanent = game.getPermanent(attachment.getAttachedTo());
                        if (fromPermanent != null) {
                            fromPermanent.removeAttachment(attachment.getId(), source, game);
                        }
                    }
                }
                sourcePermanent.addAttachment(attachment.getId(), source, game);
                game.informPlayers(attachment.getLogName() + " was attached to " + sourcePermanent.getLogName());
            }
        });
        return true;
    }
}
