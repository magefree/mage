package mage.cards.i;

import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IgnobleHierarch extends CardImpl {

    public IgnobleHierarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Exalted
        this.addAbility(new ExaltedAbility());

        // {T}: Add {B}, {R}, or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private IgnobleHierarch(final IgnobleHierarch card) {
        super(card);
    }

    @Override
    public IgnobleHierarch copy() {
        return new IgnobleHierarch(this);
    }
}
