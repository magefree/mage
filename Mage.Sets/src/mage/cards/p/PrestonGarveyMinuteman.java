package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SettlementToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrestonGarveyMinuteman extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("each enchanted permanent you control");

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public PrestonGarveyMinuteman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, create a green Aura enchantment token named Settlement attached to up to one target land you control. It has enchant land and "Enchanted land has '{T}: Add one mana of any color.'"
        Ability ability = new BeginningOfCombatTriggeredAbility(new PrestonGarveyMinutemanEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);

        // Whenever Preston Garvey, Minuteman attacks, untap each enchanted permanent you control.
        this.addAbility(new AttacksTriggeredAbility(new UntapAllEffect(filter)));
    }

    private PrestonGarveyMinuteman(final PrestonGarveyMinuteman card) {
        super(card);
    }

    @Override
    public PrestonGarveyMinuteman copy() {
        return new PrestonGarveyMinuteman(this);
    }
}

class PrestonGarveyMinutemanEffect extends OneShotEffect {

    PrestonGarveyMinutemanEffect() {
        super(Outcome.Benefit);
        staticText = "create a green Aura enchantment token named Settlement " +
                "attached to up to one target land you control. It has enchant land and " +
                "\"Enchanted land has '{T}: Add one mana of any color.'\"";
    }

    private PrestonGarveyMinutemanEffect(final PrestonGarveyMinutemanEffect effect) {
        super(effect);
    }

    @Override
    public PrestonGarveyMinutemanEffect copy() {
        return new PrestonGarveyMinutemanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && new SettlementToken().putOntoBattlefield(
                1, game, source, source.getControllerId(), false,
                false, null, permanent.getId()
        );
    }
}
