package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PrismaticAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.PerpetualUtil;

import java.util.Collections;
import java.util.UUID;

public final class VonaDeIedoTheAntifex extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland, nontoken permanent an opponent controls");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public VonaDeIedoTheAntifex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(new MenaceAbility(false));
        Ability ability = new EntersBattlefieldTriggeredAbility(new VonaDeIedoTheAntifexEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private VonaDeIedoTheAntifex(final VonaDeIedoTheAntifex card) {
        super(card);
    }

    @Override
    public VonaDeIedoTheAntifex copy() {
        return new VonaDeIedoTheAntifex(this);
    }
}

class VonaDeIedoTheAntifexEffect extends OneShotEffect {

    VonaDeIedoTheAntifexEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target nonland, nontoken permanent an opponent controls. Then you may discard a card. If you do, conjure a duplicate of that permanent into your hand, and the duplicate perpetually gains prismatic";
    }

    private VonaDeIedoTheAntifexEffect(final VonaDeIedoTheAntifexEffect effect) {
        super(effect);
    }

    @Override
    public VonaDeIedoTheAntifexEffect copy() {
        return new VonaDeIedoTheAntifexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        Card original = game.getCard(permanent.getId());
        String duplicateName = original == null ? null : original.getName();
        if (duplicateName != null && permanent.isCopy()) {
            MageObject copiable = permanent.getCopyFrom();
            for (ContinuousEffect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
                if (effect instanceof CopyEffect && ((CopyEffect) effect).getSourceId().equals(permanent.getId())) {
                    copiable = ((CopyEffect) effect).getTarget();
                }
            }
            if (copiable != null) {
                duplicateName = copiable.getName();
            }
        }
        permanent.destroy(source, game);
        if (duplicateName == null || !player.chooseUse(outcome, "Discard a card to conjure a duplicate?", source, game)
                || player.discard(1, false, false, source, game).isEmpty()) {
            return true;
        }
        Card duplicate = ConjureCardEffect.createCard(duplicateName);
        if (duplicate == null) {
            return false;
        }
        game.loadCards(Collections.singleton(duplicate), player.getId());
        PerpetualUtil.addAbility(duplicate, new PrismaticAbility(), source, game);
        return player.moveCards(game.getCard(duplicate.getId()), Zone.HAND, source, game);
    }
}
