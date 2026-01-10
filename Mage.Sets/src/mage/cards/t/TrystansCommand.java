package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class TrystansCommand extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELF);
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures target player controls");

    static {
        filter2.add(TargetController.SOURCE_TARGETS.getControllerPredicate());
    }

    public TrystansCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.SORCERY}, "{4}{B}{G}");

        this.subtype.add(SubType.ELF);

        // Choose two --
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Create a token that's a copy of target Elf you control.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // * Return one or two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addMode(
                new Mode(new ReturnFromGraveyardToHandTargetEffect())
                        .addTarget(new TargetCardInYourGraveyard(
                                1, 2, StaticFilters.FILTER_CARD_PERMANENTS
                        ))
        );

        // * Destroy target creature or enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT)));

        // * Creatures target player controls get +3/+3 until end of turn. Untap them.
        Mode mode = new Mode(new BoostAllEffect(3, 3, Duration.EndOfTurn, filter2, false));
        mode.addEffect(new TrystansCommandUntapEffect());
        mode.addTarget(new TargetPlayer());
        this.getSpellAbility().addMode(mode);
    }

    private TrystansCommand(final TrystansCommand card) {
        super(card);
    }

    @Override
    public TrystansCommand copy() {
        return new TrystansCommand(this);
    }
}

class TrystansCommandUntapEffect extends OneShotEffect {

    TrystansCommandUntapEffect() {
        super(Outcome.Benefit);
        this.staticText = "untap them";
    }

    private TrystansCommandUntapEffect(final TrystansCommandUntapEffect effect) {
        super(effect);
    }

    @Override
    public TrystansCommandUntapEffect copy() {
        return new TrystansCommandUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }
}
