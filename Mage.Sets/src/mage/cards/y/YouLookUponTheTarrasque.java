package mage.cards.y;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouLookUponTheTarrasque extends CardImpl {

    private static final FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer(
            "you and creatures you control",
            StaticFilters.FILTER_CONTROLLED_CREATURES, new FilterPlayer()
    );

    static {
        filter.getPlayerFilter().add(TargetController.YOU.getPlayerPredicate());
    }

    public YouLookUponTheTarrasque(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}");

        // Choose one —
        // • Run and Hide — Prevent all combat damage that would be dealt to you and creatures you control this turn.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, filter, true));
        this.getSpellAbility().withFirstModeFlavorWord("Run and Hide");

        // • Gather Your Courage — Target creature gets +5/+5 and gains indestructible until end of turn. All creatures your opponents control able to block that creature this turn do so.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(5, 5)
                .setText("target creature gets +5/+5"))
                .addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                        .setText("and gains indestructible until end of turn"))
                .addEffect(new YouLookUponTheTarrasqueEffect())
                .addTarget(new TargetCreaturePermanent())
                .withFlavorWord("Gather Your Courage"));
    }

    private YouLookUponTheTarrasque(final YouLookUponTheTarrasque card) {
        super(card);
    }

    @Override
    public YouLookUponTheTarrasque copy() {
        return new YouLookUponTheTarrasque(this);
    }
}

class YouLookUponTheTarrasqueEffect extends RequirementEffect {

    public YouLookUponTheTarrasqueEffect() {
        super(Duration.EndOfTurn);
        staticText = "All creatures your opponents control able to block that creature this turn do so";
    }

    public YouLookUponTheTarrasqueEffect(final YouLookUponTheTarrasqueEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attackingCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        return attackingCreature != null
                && attackingCreature.isAttacking()
                && game
                .getOpponents(source.getControllerId())
                .contains(permanent.getControllerId())
                && permanent
                .canBlock(this.getTargetPointer().getFirst(game, source), game);
    }

    @Override
    public boolean mustAttack(Game game) {
        return false;
    }

    @Override
    public boolean mustBlock(Game game) {
        return true;
    }

    @Override
    public UUID mustBlockAttacker(Ability source, Game game) {
        return this.getTargetPointer().getFirst(game, source);
    }

    @Override
    public YouLookUponTheTarrasqueEffect copy() {
        return new YouLookUponTheTarrasqueEffect(this);
    }
}
