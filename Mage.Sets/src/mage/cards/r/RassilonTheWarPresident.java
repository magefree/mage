package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConspireAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author inuenc
 */
public final class RassilonTheWarPresident extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("noncreature spells you cast from exile");

    static {
        filter.add(new CastFromZonePredicate(Zone.EXILED));
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public RassilonTheWarPresident(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, you lose 2 life and exile the top card of your library. You may play that card for as long as it remains exiled.
        Ability ability = (new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(2)));

        ability.addEffect(new RassilonTheWarPresidentExileEffect().concatBy("and"));

        this.addAbility(ability);

        // Each noncreature spell you cast from exile has conspire.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new ConspireAbility(ConspireAbility.ConspireTargets.MORE), filter)
                        .setText("Each noncreature spell you cast from exile has conspire. " +
                                "<i>(As you cast that spell, you may tap two untapped creatures you control that share a color with it. " +
                                "When you do, copy it and you may choose new targets for the copy.)</i>")
        ));
    }

    private RassilonTheWarPresident(final RassilonTheWarPresident card) {
        super(card);
    }

    @Override
    public RassilonTheWarPresident copy() {
        return new RassilonTheWarPresident(this);
    }
}

class RassilonTheWarPresidentExileEffect extends OneShotEffect {

    RassilonTheWarPresidentExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. You may play that card for as long as it remains exiled";
    }

    private RassilonTheWarPresidentExileEffect(final RassilonTheWarPresidentExileEffect effect) {
        super(effect);
    }

    @Override
    public RassilonTheWarPresidentExileEffect copy() {
        return new RassilonTheWarPresidentExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent == null || controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        Library library = controller.getLibrary();
        Card card = library.getFromTop(game);
        if (card == null) {
            return true;
        }
        String exileZoneName = "Exile — Can be played by " + controller.getName();
        controller.moveCardsToExile(
                card, source, game, true, CardUtil.getExileZoneId(
                        exileZoneName,
                        game
                ), exileZoneName
        );
        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Duration.EndOfGame);
        effect.setTargetPointer(new FixedTarget(card, game));
        game.addEffect(effect, source);
        return true;
    }
}