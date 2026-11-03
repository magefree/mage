package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;
import mage.util.functions.CopyApplier;

/**
 * @author muz
 */
public final class VoqTorchbearer extends CardImpl {

    public VoqTorchbearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.SPY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // ChoH'a' -- You may have Voq enter as a copy of a creature card in a graveyard, except he's a Klingon Spy in addition to his other types and has menace.
        this.addAbility(new EntersBattlefieldAbility(new VoqTorchbearerEffect(), true).withFlavorWord("ChoH'a'"));
    }

    private VoqTorchbearer(final VoqTorchbearer card) {
        super(card);
    }

    @Override
    public VoqTorchbearer copy() {
        return new VoqTorchbearer(this);
    }
}

class VoqTorchbearerEffect extends OneShotEffect {

    VoqTorchbearerEffect() {
        super(Outcome.Copy);
        staticText = "as a copy of a creature card in a graveyard, "
            + "except he's a Klingon Spy in addition to his other types and has menace";
    }

    private VoqTorchbearerEffect(final VoqTorchbearerEffect effect) {
        super(effect);
    }

    @Override
    public VoqTorchbearerEffect copy() {
        return new VoqTorchbearerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Target target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        target.withNotTarget(true);

        if (target.canChoose(source.getControllerId(), source, game)) {
            player.choose(outcome, target, source, game);
            Card copyFromCard = game.getCard(target.getFirstTarget());
            if (copyFromCard != null) {
                Permanent newBluePrint = new PermanentCard(copyFromCard, source.getControllerId(), game);
                newBluePrint.assignNewId();
                VoqTorchbearerCopyApplier applier = new VoqTorchbearerCopyApplier();
                applier.apply(game, newBluePrint, source, source.getSourceId());
                CopyEffect copyEffect = new CopyEffect(Duration.Custom, newBluePrint, source.getSourceId());
                game.addEffect(copyEffect, source);

                return true;
            }
        }
        return false;
    }
}

class VoqTorchbearerCopyApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.addSubType(SubType.KLINGON, SubType.SPY);
        blueprint.getAbilities().add(new MenaceAbility());
        return true;
    }
}
