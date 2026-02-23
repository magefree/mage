package mage.cards.p;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.SubType;
import mage.game.Game;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class PurpleDragonPunks extends CardImpl {

    public PurpleDragonPunks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add {R}. Spend this mana only to cast an artifact spell or to activate an ability.
        this.addAbility(new ConditionalColoredManaAbility(Mana.RedMana(1), new PurpleDragonPunksManaBuilder()));
    }

    private PurpleDragonPunks(final PurpleDragonPunks card) {
        super(card);
    }

    @Override
    public PurpleDragonPunks copy() {
        return new PurpleDragonPunks(this);
    }
}

class PurpleDragonPunksManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new PurpleDragonPunksConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or to activate an ability";
    }
}

class PurpleDragonPunksConditionalMana extends ConditionalMana {

    PurpleDragonPunksConditionalMana(Mana mana) {
        super(mana);
        addCondition(PurpleDragonPunksCondition.instance);
    }
}

enum PurpleDragonPunksCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && (object.isArtifact(game) || source.isActivated());
    }
}
