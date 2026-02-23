package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class SplinterRadicalRat extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.NINJA, "Ninja");

    public SplinterRadicalRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If any ability of a Ninja creature you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new SplinterRadicalRatEffect()));

        // {1}{U}: Target Ninja can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SplinterRadicalRat(final SplinterRadicalRat card) {
        super(card);
    }

    @Override
    public SplinterRadicalRat copy() {
        return new SplinterRadicalRat(this);
    }
}

class SplinterRadicalRatEffect extends ReplacementEffectImpl {

    SplinterRadicalRatEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a triggered ability of an Ninja you control triggers, that ability triggers an additional time";
    }

    private SplinterRadicalRatEffect(final SplinterRadicalRatEffect effect) {
        super(effect);
    }

    @Override
    public SplinterRadicalRatEffect copy() {
        return new SplinterRadicalRatEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.hasSubtype(SubType.NINJA, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
