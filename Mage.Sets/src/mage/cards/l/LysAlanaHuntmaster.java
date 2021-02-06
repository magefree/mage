package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class LysAlanaHuntmaster extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Elf spell");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public LysAlanaHuntmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new ElfWarriorToken()), filter, true));
    }

    private LysAlanaHuntmaster(final LysAlanaHuntmaster card) {
        super(card);
    }

    @Override
    public LysAlanaHuntmaster copy() {
        return new LysAlanaHuntmaster(this);
    }
}
