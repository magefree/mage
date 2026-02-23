package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutOntoBattlefieldEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfIkoria extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCreatureCard("non-Human creature card with mana value X or less");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
        filter.add(InvasionOfIkoriaPredicate.instance);
    }

    public InvasionOfIkoria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{X}{G}{G}",
                "Zilortha, Apex of Ikoria",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DINOSAUR}, "G"
        );

        // Invasion of Ikoria
        this.getLeftHalfCard().setStartingDefense(6);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Ikoria enters the battlefield, search your library and/or graveyard for a non-Human creature card with mana value X or less and put it onto the battlefield. If you search your library this way, shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutOntoBattlefieldEffect(filter)));

        // Zilortha, Apex of Ikoria
        this.getRightHalfCard().setPT(8, 8);

        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // For each non-Human creature you control, you may have that creature assign its combat damage as though it weren't blocked.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ZilorthaApexOfIkoriaEffect()));
    }

    private InvasionOfIkoria(final InvasionOfIkoria card) {
        super(card);
    }

    @Override
    public InvasionOfIkoria copy() {
        return new InvasionOfIkoria(this);
    }
}

enum InvasionOfIkoriaPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= GetXValue.instance.calculate(game, input.getSource(), null);
    }
}

class ZilorthaApexOfIkoriaEffect extends AsThoughEffectImpl {

    ZilorthaApexOfIkoriaEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "for each non-Human creature you control, you may have that " +
                "creature assign its combat damage as though it weren't blocked";
    }

    private ZilorthaApexOfIkoriaEffect(ZilorthaApexOfIkoriaEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(sourceId);
        return controller != null
                && permanent != null
                && permanent.isControlledBy(controller.getId())
                && !permanent.hasSubtype(SubType.HUMAN, game)
                && controller.chooseUse(Outcome.Damage, "Have " + permanent.getLogName()
                + " assign damage as though it weren't blocked?", source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ZilorthaApexOfIkoriaEffect copy() {
        return new ZilorthaApexOfIkoriaEffect(this);
    }
}
