package mage.cards.k;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarfellHarbinger extends CardImpl {

    public KarfellHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {U}. Spend this mana only to foretell a card from your hand or cast an instant or sorcery spell.
        this.addAbility(new ConditionalColoredManaAbility(new Mana(ManaType.BLUE, 1), new KarfellHarbingerManaBuilder()));
    }

    private KarfellHarbinger(final KarfellHarbinger card) {
        super(card);
    }

    @Override
    public KarfellHarbinger copy() {
        return new KarfellHarbinger(this);
    }
}

class KarfellHarbingerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new KarfellHarbingerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to foretell a card from your hand or cast an instant or sorcery spell.";
    }
}

class KarfellHarbingerConditionalMana extends ConditionalMana {

    KarfellHarbingerConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to foretell a card from your hand or cast an instant or sorcery spell.";
        addCondition(new KarfellHarbingerManaCondition());
    }
}

class KarfellHarbingerManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            MageObject object = game.getObject(source);
            return object != null && object.isInstantOrSorcery(game);
        }
        return source instanceof ForetellAbility;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
