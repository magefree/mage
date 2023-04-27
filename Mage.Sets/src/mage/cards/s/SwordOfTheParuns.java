
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedToTappedCondition;
import mage.abilities.condition.common.EquipmentAttachedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class SwordOfTheParuns extends CardImpl {

    private static final FilterCreaturePermanent filterTapped = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterUntapped = new FilterCreaturePermanent();
    static {
        filterTapped.add(TappedPredicate.TAPPED);
        filterUntapped.add(TappedPredicate.UNTAPPED);
    }

    public SwordOfTheParuns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // As long as equipped creature is tapped, tapped creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(2,0, Duration.WhileOnBattlefield, filterTapped),
                new CompoundCondition(EquipmentAttachedCondition.instance, AttachedToTappedCondition.instance),
                "As long as equipped creature is tapped, tapped creatures you control get +2/+0"
        )));

        // As long as equipped creature is untapped, untapped creatures you control get +0/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(0,2, Duration.WhileOnBattlefield, filterUntapped),
                new CompoundCondition(EquipmentAttachedCondition.instance, new InvertCondition(AttachedToTappedCondition.instance)),
                "As long as equipped creature is untapped, untapped creatures you control get +0/+2"
        )));

        // {3}: You may tap or untap equipped creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MayTapOrUntapAttachedEffect(), new GenericManaCost(3)));
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private SwordOfTheParuns(final SwordOfTheParuns card) {
        super(card);
    }

    @Override
    public SwordOfTheParuns copy() {
        return new SwordOfTheParuns(this);
    }
}

class MayTapOrUntapAttachedEffect extends OneShotEffect {

    public MayTapOrUntapAttachedEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may tap or untap equipped creature.";
    }

    public MayTapOrUntapAttachedEffect(final MayTapOrUntapAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null) {
            equipment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
            Player player = game.getPlayer(source.getControllerId());
            if (equipedCreature != null && player != null) {
                if (equipedCreature.isTapped()) {
                    if (player.chooseUse(Outcome.Untap, "Untap equipped creature?", source, game)) {
                        equipedCreature.untap(game);
                    }
                } else {
                    if (player.chooseUse(Outcome.Tap, "Tap equipped creature?", source, game)) {
                        equipedCreature.tap(source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public MayTapOrUntapAttachedEffect copy() {
        return new MayTapOrUntapAttachedEffect(this);
    }
}
