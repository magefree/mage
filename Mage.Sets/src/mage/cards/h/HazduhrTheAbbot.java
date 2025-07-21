package mage.cards.h;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801 & L_J
 */
public final class HazduhrTheAbbot extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("white creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public HazduhrTheAbbot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {X}, {T}: The next X damage that would be dealt this turn to target white creature you control is dealt to Hazduhr the Abbot instead.
        Ability ability = new SimpleActivatedAbility(new HazduhrTheAbbotRedirectDamageEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HazduhrTheAbbot(final HazduhrTheAbbot card) {
        super(card);
    }

    @Override
    public HazduhrTheAbbot copy() {
        return new HazduhrTheAbbot(this);
    }
}

class HazduhrTheAbbotRedirectDamageEffect extends RedirectionEffect {

    public HazduhrTheAbbotRedirectDamageEffect(Duration duration) {
        super(duration, 0, UsageType.ACCORDING_DURATION);
        this.staticText = "The next X damage that would be dealt this turn to target white creature you control is dealt to {this} instead";
    }

    private HazduhrTheAbbotRedirectDamageEffect(final HazduhrTheAbbotRedirectDamageEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        amountToRedirect = CardUtil.getSourceCostsTag(game, source, "X", 0);
    }

    @Override
    public HazduhrTheAbbotRedirectDamageEffect copy() {
        return new HazduhrTheAbbotRedirectDamageEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null
                || !event.getTargetId().equals(getTargetPointer().getFirst(game, source))
                || event.getTargetId() == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent();
        target.add(source.getSourceId(), game);
        redirectTarget = target;
        return true;
    }
}
