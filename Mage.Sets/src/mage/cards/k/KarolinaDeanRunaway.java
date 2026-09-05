package mage.cards.k;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class KarolinaDeanRunaway extends CardImpl {

    public KarolinaDeanRunaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your first main phase, add {W}{U}{B}{R}{G}. This mana can't be spent to cast spells from your hand.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
            new AddConditionalManaEffect(
                new Mana(1, 1, 1, 1, 1, 0, 0, 0),
                new KarolinaDeanRunawayManaBuilder()
            )
        ));

    }

    private KarolinaDeanRunaway(final KarolinaDeanRunaway card) {
        super(card);
    }

    @Override
    public KarolinaDeanRunaway copy() {
        return new KarolinaDeanRunaway(this);
    }
}

class KarolinaDeanRunawayManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new KarolinaDeanRunawayConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "This mana can't be spent to cast spells from your hand";
    }
}

class KarolinaDeanRunawayConditionalMana extends ConditionalMana {

    KarolinaDeanRunawayConditionalMana(Mana mana) {
        super(mana);
        staticText = "This mana can't be spent to cast spells from your hand";
        addCondition(KarolinaDeanRunawayManaCondition.instance);
    }
}

enum KarolinaDeanRunawayManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return true;
        }
        MageObject object = game.getObject(source);
        if (!source.isControlledBy(game.getOwnerId(object))) {
            return false;
        }
        if (object instanceof Spell) {
            return ((Spell) object).getFromZone() != Zone.HAND;
        }
        // checking mana without real cast
        return game.inCheckPlayableState() && game.getState().getZone(source.getSourceId()) != Zone.HAND;
    }
}
