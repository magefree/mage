package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MistSyndicateNaga extends CardImpl {

    public MistSyndicateNaga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Ninjutsu {2}{U}
        this.addAbility(new NinjutsuAbility("{2}{U}"));

        // Whenever Mist-Syndicate Naga deals combat damage to a player, create a token that's a copy of Mist-Syndicate Naga.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenCopySourceEffect(1), false
        ));
    }

    private MistSyndicateNaga(final MistSyndicateNaga card) {
        super(card);
    }

    @Override
    public MistSyndicateNaga copy() {
        return new MistSyndicateNaga(this);
    }
}
