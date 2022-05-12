package mage.cards.n;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NikoDefiesDestiny extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with foretell from your graveyard");

    static {
        filter.add(new AbilityPredicate(ForetellAbility.class));
    }

    private static final Hint hint = new ValueHint(
            "Foretold cards you own in exile", NikoDefiesDestinyValue.ONE
    );

    public NikoDefiesDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — You gain 2 life for each foretold card you own in exile.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, new GainLifeEffect(NikoDefiesDestinyValue.TWO)
        );

        // II — Add {W}{U}. Spend this mana only to foretell cards or cast spells that have foretell.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new AddConditionalManaEffect(
                        new Mana(1, 1, 0, 0, 0, 0, 0, 0),
                        new NikoDefiesDestinyManaBuilder()
                )
        );

        // III — Return target card with foretell from your graveyard to your hand.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III, SagaChapter.CHAPTER_III,
                new ReturnFromGraveyardToHandTargetEffect(), new TargetCardInYourGraveyard(filter)
        );

        this.addAbility(sagaAbility.addHint(hint));
    }

    private NikoDefiesDestiny(final NikoDefiesDestiny card) {
        super(card);
    }

    @Override
    public NikoDefiesDestiny copy() {
        return new NikoDefiesDestiny(this);
    }
}

enum NikoDefiesDestinyValue implements DynamicValue {
    ONE(1),
    TWO(2);

    private final int amount;

    NikoDefiesDestinyValue(int amount) {
        this.amount = amount;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }
        Collection<ExileZone> exileZones = game.getState().getExile().getExileZones();
        Cards cardsForetoldInExileZones = new CardsImpl();
        FilterCard filter = new FilterCard();
        filter.add(new OwnerIdPredicate(controller.getId()));
        for (ExileZone exile : exileZones) {
            for (Card card : exile.getCards(filter, game)) {
                // verify that the card is actually Foretold
                UUID exileId = CardUtil.getExileZoneId(card.getId().toString() + "foretellAbility", game);
                if (exileId != null) {
                    if (game.getState().getExile().getExileZone(exileId) != null) {
                        cardsForetoldInExileZones.add(card);
                    }
                }
            }
        }
        return amount * cardsForetoldInExileZones.size();
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "foretold card you own in exile";
    }

    @Override
    public String toString() {
        return "" + amount;
    }
}

class NikoDefiesDestinyManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new NikoDefiesDestinyConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to foretell cards or cast spells that have foretell.";
    }
}

class NikoDefiesDestinyConditionalMana extends ConditionalMana {

    NikoDefiesDestinyConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to foretell cards or cast spells that have foretell.";
        addCondition(new NikoDefiesDestinyManaCondition());
    }
}

class NikoDefiesDestinyManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            return object != null && object.getAbilities().containsClass(ForetellAbility.class);
        }
        return source instanceof ForetellAbility;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
