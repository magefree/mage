package mage.cards.t;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
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
        this.getSpellAbility().addMode(new Mode(new TrystansCommandEffect()).addTarget(new TargetPlayer()));
    }

    private TrystansCommand(final TrystansCommand card) {
        super(card);
    }

    @Override
    public TrystansCommand copy() {
        return new TrystansCommand(this);
    }
}

class TrystansCommandEffect extends ContinuousEffectImpl {

    TrystansCommandEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "creatures target player controls get +3/+3 until end of turn";
    }

    private TrystansCommandEffect(final TrystansCommandEffect effect) {
        super(effect);
    }

    @Override
    public TrystansCommandEffect copy() {
        return new TrystansCommandEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(3);
                permanent.addToughness(3);
            } else {
                it.remove();
            }
        }
        return true;
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
