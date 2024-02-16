package mage.cards.h;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HuntingTriad extends CardImpl {

    public HuntingTriad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{3}{G}");
        this.subtype.add(SubType.ELF);

        this.getSpellAbility().addEffect(new CreateTokenEffect(new ElfWarriorToken(), 3));
        this.addAbility(new ReinforceAbility(3, new ManaCostsImpl<>("{3}{G}")));
    }

    private HuntingTriad(final HuntingTriad card) {
        super(card);
    }

    @Override
    public HuntingTriad copy() {
        return new HuntingTriad(this);
    }
}
