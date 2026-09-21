package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.GingerbruteToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GingerQueenOfSweets extends CardImpl {

    public GingerQueenOfSweets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // When Ginger enters, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new BecomesMonarchSourceEffect()
        ).addHint(MonarchHint.instance));

        // {2}, {T}, Sacrifice Ginger: You gain 6 life.
        Ability ability = new SimpleActivatedAbility(
            new GainLifeEffect(6),
            new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

        // At the beginning of each upkeep, if you're the monarch, create a Gingerbrute token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            TargetController.ANY,
            new CreateTokenEffect(new GingerbruteToken()),
            false
        ).withInterveningIf(MonarchIsSourceControllerCondition.instance).addHint(MonarchHint.instance));
    }

    private GingerQueenOfSweets(final GingerQueenOfSweets card) {
        super(card);
    }

    @Override
    public GingerQueenOfSweets copy() {
        return new GingerQueenOfSweets(this);
    }
}
