
package mage.cards.s;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectPlayer;
import mage.filter.predicate.ObjectPlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class ShellOfTheLastKappa extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery spell that targets you");

    static {
        filter.add(new TargetYouPredicate());
        filter.add(Predicates.or(new CardTypePredicate(CardType.INSTANT), new CardTypePredicate(CardType.SORCERY)));
    }

    public ShellOfTheLastKappa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        addSuperType(SuperType.LEGENDARY);

        // {3}, {tap}: Exile target instant or sorcery spell that targets you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShellOfTheLastKappaEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetSpell(filter);
        ability.addTarget(target);
        this.addAbility(ability);
        // {3}, {tap}, Sacrifice Shell of the Last Kappa: You may cast a card exiled with Shell of the Last Kappa without paying its mana cost.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShellOfTheLastKappaCastEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    public ShellOfTheLastKappa(final ShellOfTheLastKappa card) {
        super(card);
    }

    @Override
    public ShellOfTheLastKappa copy() {
        return new ShellOfTheLastKappa(this);
    }
}

class ShellOfTheLastKappaEffect extends OneShotEffect {

    public ShellOfTheLastKappaEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target instant or sorcery spell that targets you";
    }

    public ShellOfTheLastKappaEffect(final ShellOfTheLastKappaEffect effect) {
        super(effect);
    }

    @Override
    public ShellOfTheLastKappaEffect copy() {
        return new ShellOfTheLastKappaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent == null) {
                sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            }
            if (sourcePermanent != null) {
                game.getStack().counter(spell.getId(), source.getSourceId(), game);
                Card card = spell.getCard();
                card.moveToExile(CardUtil.getCardExileZoneId(game, source), sourcePermanent.getName(), source.getSourceId(), game);
            }
        }
        return false;
    }
}

class ShellOfTheLastKappaCastEffect extends OneShotEffect {

    public ShellOfTheLastKappaCastEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "You may cast a card exiled with {this} without paying its mana cost";
    }

    public ShellOfTheLastKappaCastEffect(final ShellOfTheLastKappaCastEffect effect) {
        super(effect);
    }

    @Override
    public ShellOfTheLastKappaCastEffect copy() {
        return new ShellOfTheLastKappaCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInExile target = new TargetCardInExile(new FilterCard(), CardUtil.getCardExileZoneId(game, source));
            if (controller.choose(Outcome.PlayForFree, game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source)), target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    game.getExile().removeCard(card, game);
                    return controller.cast(card.getSpellAbility(), game, true, new MageObjectReference(source.getSourceObject(game), game));
                }
            }
        }
        return false;
    }
}

class TargetYouPredicate implements ObjectPlayerPredicate<ObjectPlayer<StackObject>> {

    @Override
    public boolean apply(ObjectPlayer<StackObject> input, Game game) {
        UUID controllerId = input.getPlayerId();
        if (controllerId == null) {
            return false;
        }

        for (Target target : input.getObject().getStackAbility().getTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (controllerId.equals(targetId)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "spell that targets you";
    }
}
