package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.watchers.common.AbilityResolvedWatcher;

/**
 *
 * @author Grath
 */
public final class ZimoneMysteryUnraveler extends CardImpl {

    public ZimoneMysteryUnraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, manifest dread if this is the first time this ability has resolved this turn. Otherwise, you may turn a permanent you control face up.
        Ability ability = new LandfallAbility(new IfAbilityHasResolvedXTimesEffect(
                Outcome.Benefit, 1, new ManifestDreadEffect()
        ).setText("manifest dread if this is the first time this ability has resolved this turn."), false);
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(Outcome.Benefit, 2, true, new ZimoneMysteryUnravelerEffect()));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private ZimoneMysteryUnraveler(final ZimoneMysteryUnraveler card) {
        super(card);
    }

    @Override
    public ZimoneMysteryUnraveler copy() {
        return new ZimoneMysteryUnraveler(this);
    }
}

class ZimoneMysteryUnravelerEffect extends OneShotEffect {

    public ZimoneMysteryUnravelerEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may turn a permanent you control face up";
    }

    protected ZimoneMysteryUnravelerEffect(final ZimoneMysteryUnravelerEffect effect) {
        super(effect);
    }

    @Override
    public ZimoneMysteryUnravelerEffect copy() {
        return new ZimoneMysteryUnravelerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        FilterControlledPermanent filter = new FilterControlledPermanent("a permanent you control");
        filter.add(FaceDownPredicate.instance);
        if (controller != null) {
            TargetControlledPermanent target = new TargetControlledPermanent(0, 1, filter, true);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            return permanent != null && permanent.turnFaceUp(source, game, source.getControllerId());
        }
        return false;
    }
}