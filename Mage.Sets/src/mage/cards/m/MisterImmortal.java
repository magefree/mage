package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class MisterImmortal extends CardImpl {

    public MisterImmortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{G}: Return this card from your graveyard or from exile to the battlefield tapped.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.ALL,
                new ReturnToBattlefieldUnderOwnerControlSourceEffect(true, -1)
                        .setText("return this card from your graveyard or from exile to the battlefield tapped"),
                new ManaCostsImpl<>("{2}{G}"), MisterImmortalCondition.instance
        ).hideCondition());
    }

    private MisterImmortal(final MisterImmortal card) {
        super(card);
    }

    @Override
    public MisterImmortal copy() {
        return new MisterImmortal(this);
    }
}

enum MisterImmortalCondition implements Condition {
    instance;
    private static final List<Zone> zones = Arrays.asList(Zone.GRAVEYARD, Zone.EXILED);

    @Override
    public boolean apply(Game game, Ability source) {
        return zones.contains(game.getState().getZone(source.getSourceId()));
    }

    @Override
    public String toString() {
        return "{this} is in your graveyard or in exile";
    }
}
