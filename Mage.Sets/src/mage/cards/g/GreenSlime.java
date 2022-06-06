package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreenSlime extends CardImpl {

    private static final FilterStackObject filter
            = new FilterStackObject("activated or triggered ability from an artifact or enchantment source");

    static {
        filter.add(GreenSlimePredicate.instance);
    }

    public GreenSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Green Slime enters the battlefield, counter target activated or triggered ability from an artifact or enchantment source. If a permanent's ability is countered this way, destroy that permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GreenSlimeEffect());
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability);

        // Foretell {G}
        this.addAbility(new ForetellAbility(this, "{G}"));
    }

    private GreenSlime(final GreenSlime card) {
        super(card);
    }

    @Override
    public GreenSlime copy() {
        return new GreenSlime(this);
    }
}

enum GreenSlimePredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (!(input instanceof StackAbility)) {
            return false;
        }
        MageObject sourceObject = input.getStackAbility().getSourceObject(game);
        return sourceObject != null && (sourceObject.isArtifact(game) || sourceObject.isEnchantment(game));
    }
}

class GreenSlimeEffect extends OneShotEffect {

    GreenSlimeEffect() {
        super(Outcome.Benefit);
        staticText = "counter target activated or triggered ability from an artifact or enchantment source. " +
                "If a permanent's ability is countered this way, destroy that permanent";
    }

    private GreenSlimeEffect(final GreenSlimeEffect effect) {
        super(effect);
    }

    @Override
    public GreenSlimeEffect copy() {
        return new GreenSlimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
        if (stackObject == null) {
            return false;
        }
        Permanent permanent = stackObject.getStackAbility().getSourcePermanentIfItStillExists(game);
        if (game.getStack().counter(stackObject.getId(), source, game) && permanent != null) {
            permanent.destroy(source, game);
        }
        return true;
    }
}
