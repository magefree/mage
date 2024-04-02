package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirageMesa extends CardImpl {

    public MirageMesa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Mirage Mesa enters the battlefield tapped. As it enters, choose a color.
        Ability ability = new EntersBattlefieldTappedAbility(
                "{this} enters the battlefield tapped. As it enters, choose a color"
        );
        ability.addEffect(new ChooseColorEffect(Outcome.Benefit));
        this.addAbility(ability);

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private MirageMesa(final MirageMesa card) {
        super(card);
    }

    @Override
    public MirageMesa copy() {
        return new MirageMesa(this);
    }
}
