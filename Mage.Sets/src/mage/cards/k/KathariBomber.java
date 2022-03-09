package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.GoblinToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KathariBomber extends CardImpl {

    public KathariBomber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Kathari Bomber deals combat damage to a player, create two 1/1 red Goblin creature tokens and sacrifice Kathari Bomber.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new GoblinToken(), 2), false
        ).setTriggerPhrase("When {this} deals combat damage to a player, ");
        ability.addEffect(new SacrificeSourceEffect().concatBy("and"));
        this.addAbility(ability);

        // Unearth {3}{B}{R}
        this.addAbility(new UnearthAbility(new ManaCostsImpl("{3}{B}{R}")));
    }

    private KathariBomber(final KathariBomber card) {
        super(card);
    }

    @Override
    public KathariBomber copy() {
        return new KathariBomber(this);
    }
}
