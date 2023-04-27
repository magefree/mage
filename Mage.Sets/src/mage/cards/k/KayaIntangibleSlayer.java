package mage.cards.k;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.constants.*;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class KayaIntangibleSlayer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    public KayaIntangibleSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAYA);
        this.setStartingLoyalty(6);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // +2: Each opponent loses 3 life and you gain 3 life.
        Ability ability = new LoyaltyAbility(new LoseLifeOpponentsEffect(3), 2);
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        this.addAbility(ability);

        // 0: You draw two cards. Then each opponent may scry 1.
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(2, "you"), 0);
        ability.addEffect(new KayaIntangibleSlayerScryEffect());
        this.addAbility(ability);

        // -3: Exile target creature or enchantment. If it wasn't an Aura, create a token that's a copy of it, except it's a 1/1 white Spirit creature with flying in addition to its other types.
        ability = new LoyaltyAbility(new KayaIntangibleSlayerExileEffect(), -3);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KayaIntangibleSlayer(final KayaIntangibleSlayer card) {
        super(card);
    }

    @Override
    public KayaIntangibleSlayer copy() {
        return new KayaIntangibleSlayer(this);
    }
}

class KayaIntangibleSlayerScryEffect extends OneShotEffect {

    KayaIntangibleSlayerScryEffect() {
        super(Outcome.Benefit);
        staticText = "Then each opponent may scry 1";
    }

    private KayaIntangibleSlayerScryEffect(final KayaIntangibleSlayerScryEffect effect) {
        super(effect);
    }

    @Override
    public KayaIntangibleSlayerScryEffect copy() {
        return new KayaIntangibleSlayerScryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null && player.chooseUse(outcome, "Scry 1?", source, game)) {
                player.scry(1, source, game);
            }
        }
        return true;
    }
}

class KayaIntangibleSlayerExileEffect extends OneShotEffect {

    KayaIntangibleSlayerExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature or enchantment. If it wasn't an Aura, " +
                "create a token that's a copy of it, except it's a 1/1 white Spirit creature " +
                "with flying in addition to its other types";
    }

    private KayaIntangibleSlayerExileEffect(final KayaIntangibleSlayerExileEffect effect) {
        super(effect);
    }

    @Override
    public KayaIntangibleSlayerExileEffect copy() {
        return new KayaIntangibleSlayerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (permanent.hasSubtype(SubType.AURA, game)) {
            return true;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, CardType.CREATURE, false, 1, false,
                false, null, 1, 1, true
        );
        effect.setSavedPermanent(permanent);
        effect.setOnlyColor(ObjectColor.WHITE);
        effect.setAdditionalSubType(SubType.SPIRIT);
        effect.apply(game, source);
        return true;
    }
}
