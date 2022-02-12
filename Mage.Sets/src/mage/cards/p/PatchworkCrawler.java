package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatchworkCrawler extends CardImpl {

    public PatchworkCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{U}: Exile target creature card from your graveyard and put a +1/+1 counter on Patchwork Crawler.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // Patchwork Crawler has all activated abilities of all creature cards exiled with it.
        this.addAbility(new SimpleStaticAbility(new PatchworkCrawlerEffect()));
    }

    private PatchworkCrawler(final PatchworkCrawler card) {
        super(card);
    }

    @Override
    public PatchworkCrawler copy() {
        return new PatchworkCrawler(this);
    }
}

class PatchworkCrawlerEffect extends ContinuousEffectImpl {

    PatchworkCrawlerEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} has all activated abilities of all creature cards exiled with it";
    }

    private PatchworkCrawlerEffect(final PatchworkCrawlerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (permanent == null || exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        for (Card card : exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            for (Ability ability : card.getAbilities(game)) {
                if (ability instanceof ActivatedAbility) {
                    permanent.addAbility(ability, source.getSourceId(), game);
                }
            }
        }
        return true;
    }

    @Override
    public PatchworkCrawlerEffect copy() {
        return new PatchworkCrawlerEffect(this);
    }
}
