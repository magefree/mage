package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DelneyStreetwiseLookout extends CardImpl {

    private static final FilterCreaturePermanent filterSmall = new FilterCreaturePermanent("creatures you control with power 2 or less");
    private static final FilterCreaturePermanent filterBig = new FilterCreaturePermanent("creatures with power 3 or greater");
    static {
        filterSmall.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
        filterSmall.add(TargetController.YOU.getControllerPredicate());
        filterBig.add(new PowerPredicate(ComparisonType.OR_GREATER, 3));
    }

    public DelneyStreetwiseLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creatures you control with power 2 or less can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAllEffect(
                filterSmall, filterBig, Duration.WhileOnBattlefield
        )));

        // If an ability of a creature you control with power 2 or less triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new DelneyStreetwiseLookoutEffect()));
    }

    private DelneyStreetwiseLookout(final DelneyStreetwiseLookout card) {
        super(card);
    }

    @Override
    public DelneyStreetwiseLookout copy() {
        return new DelneyStreetwiseLookout(this);
    }
}

class DelneyStreetwiseLookoutEffect extends ReplacementEffectImpl {

    DelneyStreetwiseLookoutEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if an ability of a creature you control with power 2 or less triggers, that ability triggers an additional time";
    }

    private DelneyStreetwiseLookoutEffect(final DelneyStreetwiseLookoutEffect effect) {
        super(effect);
    }

    @Override
    public DelneyStreetwiseLookoutEffect copy() {
        return new DelneyStreetwiseLookoutEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId())
                && permanent.getPower().getValue() <= 2;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
