package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class LilysplashMentor extends CardImpl {

    public LilysplashMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {1}{G}{U}: Exile another target creature you control, then return it to the battlefield under its owner's control with a +1/+1 counter on it. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new LilysplashMentorEffect(), new ManaCostsImpl<>("{1}{G}{U}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private LilysplashMentor(final LilysplashMentor card) {
        super(card);
    }

    @Override
    public LilysplashMentor copy() {
        return new LilysplashMentor(this);
    }
}

//Copied from Planar Incision
class LilysplashMentorEffect extends OneShotEffect {

    LilysplashMentorEffect() {
        super(Outcome.Benefit);
        staticText = "Exile another target creature you control, then return it to the battlefield under its owner's control with a +1/+1 counter on it";
    }

    private LilysplashMentorEffect(final LilysplashMentorEffect effect) {
        super(effect);
    }

    @Override
    public LilysplashMentorEffect copy() {
        return new LilysplashMentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null
                && controller != null) {
            UUID exileId = CardUtil.getExileZoneId("LilysplashMentorEffectExile" + source.toString(), game);
            if (controller.moveCardsToExile(permanent, source, game, true, exileId, "")) {
                if (game.getExile().getExileZone(exileId) != null) {
                    Card exiledCard = game.getExile().getExileZone(exileId).get(permanent.getId(), game);
                    if (exiledCard != null) {
                        Counters countersToAdd = new Counters();
                        countersToAdd.addCounter(CounterType.P1P1.createInstance());
                        game.setEnterWithCounters(exiledCard.getId(), countersToAdd);
                        return controller.moveCards(exiledCard, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
            }
        }
        return false;
    }
}
