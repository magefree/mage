package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceDealtDamageCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.emblems.ChandraRoaringFlameEmblem;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.watchers.common.DamageDoneWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ChandraFireOfKaladesh extends TransformingDoubleFacedCard {

    private static final FilterSpell filter = new FilterSpell("a red spell");
    static { filter.add(new ColorPredicate(ObjectColor.RED)); }
    private static final Condition condition = new SourceDealtDamageCondition(3);

    public ChandraFireOfKaladesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN}, "{1}{R}{R}",
                "Chandra, Roaring Flame",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.CHANDRA}, "R");

        // Chandra, Fire of Kaladesh
        this.getLeftHalfCard().setPT(2, 2);

        // Whenever you cast a red spell, untap Chandra, Fire of Kaladesh.
        this.getLeftHalfCard().addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), filter, false));

        // {T}: Chandra, Fire of Kaladesh deals 1 damage to target player or planeswalker. If Chandra has dealt 3 or more damage this turn, exile her, then return her to the battlefield transformed under her owner's control.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE), condition
        ));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        ability.addWatcher(new DamageDoneWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Chandra, Roaring Flame
        this.getRightHalfCard().setStartingLoyalty(4);

        // +1: Chandra, Roaring Flame deals 2 damage to target player or planeswalker.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DamageTargetEffect(2), 1);
        loyaltyAbility.addTarget(new TargetPlayerOrPlaneswalker());
        this.getRightHalfCard().addAbility(loyaltyAbility);

        // -2: Chandra, Roaring Flame deals 2 damage to target creature.
        loyaltyAbility = new LoyaltyAbility(new DamageTargetEffect(2), -2);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());
        this.getRightHalfCard().addAbility(loyaltyAbility);

        // -7: Chandra, Roaring Flame deals 6 damage to each opponent. Each player dealt damage this way gets an emblem with "At the beginning of your upkeep, this emblem deals 3 damage to you."
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new ChandraRoaringFlameEmblemEffect(), -7));
    }

    private ChandraFireOfKaladesh(final ChandraFireOfKaladesh card) {
        super(card);
    }

    @Override
    public ChandraFireOfKaladesh copy() {
        return new ChandraFireOfKaladesh(this);
    }
}

class ChandraRoaringFlameEmblemEffect extends OneShotEffect {

    ChandraRoaringFlameEmblemEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 6 damage to each opponent. Each player dealt damage this way gets an emblem with \"At the beginning of your upkeep, this emblem deals 3 damage to you.\"";
    }

    private ChandraRoaringFlameEmblemEffect(final ChandraRoaringFlameEmblemEffect effect) { super(effect); }
    @Override public ChandraRoaringFlameEmblemEffect copy() { return new ChandraRoaringFlameEmblemEffect(this); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }
        List<Player> opponentsEmblem = new ArrayList<>();
        for (UUID playerId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null && opponent.damage(6, source, game) > 0) {
                opponentsEmblem.add(opponent);
            }
        }
        for (Player opponent : opponentsEmblem) {
            game.addEmblem(new ChandraRoaringFlameEmblem(), source.getSourceObject(game), opponent.getId());
        }
        return false;
    }
}
