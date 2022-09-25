
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author anonymous
 */
public final class HuntingWilds extends CardImpl {

    public HuntingWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Kicker {3}{G}
        this.addAbility(new KickerAbility("{3}{G}"));

        FilterLandCard filter = new FilterLandCard("Forest card");
        filter.add(SubType.FOREST.getPredicate());

        // Search your library for up to two Forest cards and put them onto the battlefield tapped. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 2, filter), true));

        // If Hunting Wilds was kicked, untap all Forests put onto the battlefield this way.
        // They become 3/3 green creatures with haste that are still lands.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new HuntingWildsEffect(), KickedCondition.ONCE));
    }

    private HuntingWilds(final HuntingWilds card) {
        super(card);
    }

    @Override
    public HuntingWilds copy() {
        return new HuntingWilds(this);
    }
}

class HuntingWildsEffect extends OneShotEffect {

    public HuntingWildsEffect() {
        super(Outcome.BecomeCreature);
        this.staticText = "Untap all Forests put onto the battlefield this way. They become 3/3 green creatures with haste that are still lands";
    }

    public HuntingWildsEffect(final HuntingWildsEffect effect) {
        super(effect);
    }

    @Override
    public HuntingWildsEffect copy() {
        return new HuntingWildsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Effect sourceEffect : source.getEffects()) {
            if (sourceEffect instanceof SearchLibraryPutInPlayEffect) {
                Cards foundCards = new CardsImpl(((SearchLibraryPutInPlayEffect) sourceEffect).getTargets());
                if (!foundCards.isEmpty()) {
                    FixedTargets fixedTargets = new FixedTargets(foundCards, game);
                    UntapTargetEffect untapEffect = new UntapTargetEffect();
                    untapEffect.setTargetPointer(fixedTargets);
                    untapEffect.apply(game, source);

                    BecomesCreatureTargetEffect becomesCreatureEffect = new BecomesCreatureTargetEffect(new HuntingWildsToken(), false, true, Duration.Custom);
                    becomesCreatureEffect.setTargetPointer(fixedTargets);
                    game.addEffect(becomesCreatureEffect, source);
                }
                return true;
            }
        }
        return false;
    }
}

class HuntingWildsToken extends TokenImpl {

    public HuntingWildsToken() {
        super("", "3/3 green creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(HasteAbility.getInstance());
    }
    public HuntingWildsToken(final HuntingWildsToken token) {
        super(token);
    }

    public HuntingWildsToken copy() {
        return new HuntingWildsToken(this);
    }
}
