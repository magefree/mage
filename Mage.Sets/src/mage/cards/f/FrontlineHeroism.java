package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.other.HasOnlySingleTargetPermanentPredicate;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AkroanSoldierToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;
import mage.util.functions.StackObjectCopyApplier;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class FrontlineHeroism extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell that targets only a single creature you control");

    static {
        filter.add(new HasOnlySingleTargetPermanentPredicate(StaticFilters.FILTER_CONTROLLED_CREATURE));
    }

    public FrontlineHeroism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // When Frontline Heroism enters, create a 1/1 red Soldier creature token with haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AkroanSoldierToken())));

        // Whenever you cast a spell that targets only a single creature you control, create a 1/1 red Soldier creature token with haste, then copy that spell. The copy targets that token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new FrontlineHeroismEffect(), filter, false));
    }

    private FrontlineHeroism(final FrontlineHeroism card) {
        super(card);
    }

    @Override
    public FrontlineHeroism copy() {
        return new FrontlineHeroism(this);
    }
}

class FrontlineHeroismEffect extends OneShotEffect {

    private static final class FrontlineHeroismApplier implements StackObjectCopyApplier {

        private final Iterator<MageObjectReferencePredicate> predicate;

        FrontlineHeroismApplier(Permanent permanent, Game game) {
            this.predicate = Arrays.asList(new MageObjectReferencePredicate(permanent, game)).iterator();
        }

        @Override
        public void modifySpell(StackObject stackObject, Game game) {
        }

        @Override
        public MageObjectReferencePredicate getNextNewTargetType() {
            if (predicate.hasNext()) {
                return predicate.next();
            }
            return null;
        }
    }

    FrontlineHeroismEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Soldier creature token with haste, " +
                "then copy that spell. The copy targets that token";
    }

    private FrontlineHeroismEffect(final FrontlineHeroismEffect effect) {
        super(effect);
    }

    @Override
    public FrontlineHeroismEffect copy() {
        return new FrontlineHeroismEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        Token token = new AkroanSoldierToken();
        token.putOntoBattlefield(1, game, source);
        Set<Permanent> permanents = token
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Permanent permanent;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                permanent = RandomUtil.randomFromCollection(permanents);
                break;
            default:
                FilterPermanent filter = new FilterPermanent("token to target with the copied spell");
                filter.add(new PermanentReferenceInCollectionPredicate(permanents, game));
                TargetPermanent target = new TargetPermanent(filter);
                target.withNotTarget(true);
                player.choose(outcome, target, source, game);
                permanent = game.getPermanent(target.getFirstTarget());
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(), false,
                1, new FrontlineHeroismApplier(permanent, game)
        );
        return true;
    }
}
