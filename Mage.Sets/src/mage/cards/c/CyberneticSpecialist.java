package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CyberneticSpecialist extends CardImpl {

    public CyberneticSpecialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When this creature enters, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // {1}, {T}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private CyberneticSpecialist(final CyberneticSpecialist card) {
        super(card);
    }

    @Override
    public CyberneticSpecialist copy() {
        return new CyberneticSpecialist(this);
    }
}
