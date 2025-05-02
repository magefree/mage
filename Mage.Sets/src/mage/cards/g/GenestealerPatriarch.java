package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GenestealerPatriarch extends CardImpl {

    public static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature defending player controls");
    private static final FilterPermanent filter2
            = new FilterCreaturePermanent("a creature with an infection counter on it");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
        filter2.add(CounterType.INFECTION.getPredicate());
    }

    public GenestealerPatriarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Genestealer's Kiss — Whenever Genestealer Patriarch attacks, put an infection counter on target creature defending player controls.
        Ability ability = new AttacksTriggeredAbility(
                new AddCountersTargetEffect(CounterType.INFECTION.createInstance())
        ).withFlavorWord("Genestealer's Kiss");
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Children of the Cult — Whenever a creature with an infection counter on it dies, you create
        // a token that's a copy of that creature, except it's a Tyranid in addition to its other types.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new GenestealerPatriarchCloneEffect(), false, filter2, false
        ).withFlavorWord("Children of the Cult"));
    }

    private GenestealerPatriarch(final GenestealerPatriarch card) {
        super(card);
    }

    @Override
    public GenestealerPatriarch copy() {
        return new GenestealerPatriarch(this);
    }
}

class GenestealerPatriarchCloneEffect extends OneShotEffect {

    GenestealerPatriarchCloneEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you create a token that's a copy of that creature, " +
                "except it's a Tyranid in addition to its other types";
    }

    private GenestealerPatriarchCloneEffect(final GenestealerPatriarchCloneEffect effect) {
        super(effect);
    }

    @Override
    public GenestealerPatriarchCloneEffect copy() {
        return new GenestealerPatriarchCloneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = (Permanent) getValue("creatureDied");
        if (controller == null || creature == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId());
        effect.setSavedPermanent(creature);
        effect.withAdditionalSubType(SubType.TYRANID);
        return effect.apply(game, source);
    }
}
