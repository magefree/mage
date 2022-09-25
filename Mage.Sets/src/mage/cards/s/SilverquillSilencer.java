package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ChosenNamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SilverquillSilencer extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with the chosen name");

    static {
        filter.add(ChosenNamePredicate.instance);
    }

    public SilverquillSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As Silverquill Silencer enters the battlefield, choose a nonland card name.
        this.addAbility(new AsEntersBattlefieldAbility(
                new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NON_LAND_NAME)
        ));

        // Whenever an opponent casts a spell with the chosen name, they lose 3 life and you draw a card.
        Ability ability = new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new LoseLifeTargetEffect(3).setText("they lose 3 life"),
                filter, false, SetTargetPointer.PLAYER
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
        this.addAbility(ability);
    }

    private SilverquillSilencer(final SilverquillSilencer card) {
        super(card);
    }

    @Override
    public SilverquillSilencer copy() {
        return new SilverquillSilencer(this);
    }
}
